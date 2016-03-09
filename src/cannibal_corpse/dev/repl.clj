(ns cannibal-corpse.dev.repl
  (:require [net.cgrand.enlive-html :as eh]
            [clojure.java.io :refer [reader]]))


(set! *print-level* 2)
(set! *print-length* 10)


(def cache (atom {}))


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
