(ns alwxblog.db.query
  (:use [korma.core])
  (:require [alwxblog.db.entities :as entities]))

(defn is-url-unique
  ([url id]
     (if (seq (select entities/posts (where {:id [not= id] :url url})))
       false
       true))
  ([url] (is-url-unique url 0)))

(defn get-posts-all []
  (select entities/posts (fields :id :url :title :date) (order :id :DESC)))

(defn get-post-by-url [url]
  (select entities/posts (where {:url url})))

(defn post-blog-add [title full_text url]
  (if (and (> (count title) 2)
           (> (count full_text) 4)
           (> (count url) 2)
           (is-url-unique url))
    (insert entities/posts
            (values {:title title
                     :full_text full_text
                     :url url
                     :date (quot (System/currentTimeMillis) 1000)}))))

(defn post-blog-update [id title full_text url]
  (if (and (> (count title) 2)
           (> (count full_text) 4)
           (> (count url) 2)
           (is-url-unique url id))
    (update entities/posts
            (set-fields {:title title
                         :full_text full_text
                         :url url})
            (where {:id [= id]}))))
