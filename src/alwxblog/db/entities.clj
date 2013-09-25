(ns alwxblog.db.entities
  (:use [korma.core])
  (:require [alwxblog.db.connection :as connection]))

(declare posts url title full_text date)

(defentity posts
  (database connection/korma-db)
  (table :posts))
