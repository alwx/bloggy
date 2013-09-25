(ns alwxblog.core
  (:gen-class)
  (:require [ring.adapter.jetty :as http]
            [ring.util.response :as response])
  (:require [alwxblog.commons :as commons :refer (users)])
  (:require [alwxblog.templates :as t])
  (:require [alwxblog.db.query :as query])
  (:require [compojure.route :as route])
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds]))
  (:use [compojure.handler :only [site]])
  (:require [compojure.handler :as handler])
  (:use [compojure.core :only [defroutes GET POST DELETE ANY context]]))

(defn get-404-error [req]
  (t/template-main req {:title "404"
                        :page-title "Not found"
                        :content (t/page-404)}))

(defn get-blog-post [req]
  (if-let [post (first (query/get-post-by-url (-> req :params :url)))]
    (t/template-main req {:title (-> post :title)
                          :page-title (-> post :title)
                          :content (t/page-blog-post post)
                          :action-link (str "/blog/"
                                            (-> req :params :url)
                                            "/edit")
                          :action-icon "icon-edit-sign"})
    (get-404-error req)))

(defn get-blog-post-edit [req]
  (if-let [post (first (query/get-post-by-url (-> req :params :url-old)))]
    (t/template-blog-add {:params
                          {:id (:id post)
                           :title (:title post)
                           :full_text (:full_text post)
                           :url (:url post)}})
    (get-404-error req)))

(defn post-blog-content [req]
  (if-let [id (-> req :params :url-old)]
    (if-let [post (first (query/get-post-by-url (-> req :params :url-old)))]
      (if (= (query/post-blog-update
              (:id post)
              (-> req :params :title)
              (-> req :params :full_text)
              (-> req :params :url)) nil)
        (get-blog-post-edit req)
        (response/redirect "/blog"))
      (get-404-error req))
    (if (= (query/post-blog-add
            (-> req :params :title)
            (-> req :params :full_text)
            (-> req :params :url)) nil)
      (t/template-blog-add req)
      (response/redirect "/blog"))))

(defroutes routes*
  (GET "/*" req
       (t/template-main req {:title "Main"
                             :page-title "alwx.me"
                             :content (t/page-main)}))
  (GET "/blog" req
       (t/template-main req {:title "Blog"
                             :content (map #(t/page-blog %)
                                           (query/get-posts-all))}))
  (GET "/blog/:url" req
       (get-blog-post req))
  (GET "/blog/:url-old/edit" req
       (friend/authenticated (get-blog-post-edit req)))
  (POST "/blog/:url-old/edit" req
        (friend/authenticated (post-blog-content req)))
  (GET "/blog-add" req
       (friend/authenticated (t/template-blog-add req)))
  (POST "/blog-add" req
        (friend/authenticated (post-blog-content req)))
  (GET "/login" req
       (t/template-main req {:title "Login" :content (t/page-login)}))
  (GET "/logout" req
       (friend/logout* (response/redirect (str (:context req) "/"))))
  (route/files "/static/")
  (route/not-found get-404-error))

(def routes (site (friend/authenticate
                   routes*
                   {:allow-anon? true
                    :login-uri "/login"
                    :default-landing-uri "/"
                    :credential-fn #(creds/bcrypt-credential-fn @users %)
                    :workflows [(workflows/interactive-form)]})))

(def app (handler/site routes))
