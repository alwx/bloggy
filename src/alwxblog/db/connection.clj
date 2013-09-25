(ns alwxblog.db.connection
  (:use [korma.db]))

(def db-name "resources/db.sqlite3")

(def db (sqlite3 {:db db-name}))

(defdb korma-db db)
