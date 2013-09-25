(ns alwxblog.templates
  (:require [net.cgrand.enlive-html :as html])
  (:require [alwxblog.commons :as commons])
  (:require [cemerick.friend :as friend])
  (:require [markdown.core :as md]))

(html/deftemplate template-main "alwxblog/templates/template_main.html"
  [req hash]
  [:head :title] (html/content (:title hash) commons/site-postfix)
  [:#page-title] (html/content (get hash :page-title (get hash :title)))
  [:#content] (html/append (get hash :content))
  [:.action] (if-let [identity (friend/identity req)]
               (html/set-attr :href (get hash :action-link "/blog-add"))
               (html/substitute nil))
  [:.action-icon] (html/add-class (get hash :action-icon "icon-plus-sign")))

(html/deftemplate template-blog-add "alwxblog/templates/template_blog_add.html"
  [req]
  [:.post-title] (html/set-attr :value (-> req :params :title))
  [:.post-text] (html/content (-> req :params :full_text))
  [:.post-url] (html/set-attr :value (-> req :params :url))
  [:.post-button] (if (-> req :params :id)
                    (html/set-attr :value "Save")
                    (html/set-attr :value "Add"))
  [:.post-form] (if (-> req :params :id)
                  (html/set-attr :action
                                 (str "/blog/" (-> req :params :url) "/edit"))
                  (html/set-attr :action "/blog-add")))

(html/defsnippet page-main "alwxblog/templates/page_main.html"
  [:article] [])

(html/defsnippet page-404 "alwxblog/templates/page_404.html"
  [:article] [])

(html/defsnippet page-blog "alwxblog/templates/page_blog.html"
  [:article]
  [{:keys [id url title date]}]
  [:#title] (html/do->
             (html/content title)
             (html/set-attr :href (str "/blog/" url)))
  [:#datetime] (html/content (commons/parse-date date)))

(html/defsnippet page-blog-post "alwxblog/templates/page_blog_post.html"
  [:.blog-page]
  [{:keys [full_text]}]
  [:#content] (html/html-content (md/md-to-html-string full_text)))

(html/defsnippet page-login "alwxblog/templates/page_login.html"
  [:#div-form] [])
