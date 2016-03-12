(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [clojure.pprint :refer [pp pprint]]
            [net.cgrand.enlive-html :as eh]
            [clojure.java.io :refer [reader]]))


(def cache (atom {}))


(defn init []
  (set! *print-level* 2)
  (set! *print-length* 10))


(defn get-resource [url]
  (if-let [cached (get @cache url)]
    cached
    (let [got (-> (reader url :encoding "UTF-8")
                (eh/html-resource))]
      (swap! cache assoc url got)
      got)))


(defn get-and-select [url & selector]
  (-> (get-resource url)
    (eh/select (vec selector))))
