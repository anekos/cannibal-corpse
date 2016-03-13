(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [clojure.pprint :refer [pp pprint cl-format]]
            [net.cgrand.enlive-html :as eh]
            [clojure.java.io :refer [reader writer file]]
            [cannibal-corpse.extractor.util :refer :all]))


(defn cache-from-file []
  (if (.exists (file ".cache"))
    (read-string (slurp ".cache"))
    {}))


(def cache (atom (cache-from-file)))


(defn restore-cache []
  (reset! cache (cache-from-file)))


(defn store-cache []
  (with-open [w (writer ".cache" :encoding "UTF-8")]
    (binding [*print-level* nil
              *print-length* nil]
      (.write w (str @cache))
      :done)))


(defn init []
  (restore-cache)
  (refresh)
  (set! *print-level* 2)
  (set! *print-length* 10))


(defn reset []
  (store-cache)
  (init))


(defn set-aliases []
  (alias 'amz 'cannibal-corpse.extractor.amazon))


(defn get-resource [url]
  (if-let [cached (get @cache url)]
    cached
    (let [got (-> (reader url :encoding "UTF-8")
                (eh/html-resource))]
      (cl-format true "fetching ~A" url)
      (swap! cache assoc url got)
      got)))


(defn get-and-select [url & selector]
  (-> (get-resource url)
    (eh/select (vec selector))))
