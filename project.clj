(defproject alwxblog "0.3"
  :description "Bloggy"
  :url "http://github.com/alwx/bloggy"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.0"]
                 [compojure "1.1.5"]
                 [enlive "1.1.4"]
                 [korma "0.3.0-RC5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [clj-time "0.6.0"]
                 [com.cemerick/friend "0.1.5"]
                 [markdown-clj "0.9.32"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler alwxblog.core/app}
  :aot [alwxblog.core])
