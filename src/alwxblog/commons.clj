(ns alwxblog.commons
  (:require [clj-time.format :as tformat])
  (:require [clj-time.coerce :as tcoerce])
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;; site basic settings
(def site-title "sample blog")
(def site-postfix (str " @ " site-title))
(def default-datetime-format (tformat/formatter "dd.MM.yyyy"))

(defn parse-date [date]
  (tformat/unparse default-datetime-format
                   (tcoerce/from-long (* date 1000))))

;; user credentials
(def users (atom {"user" {:username "user"
                          :password (creds/hash-bcrypt "secret")}}))
