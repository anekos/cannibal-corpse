(ns cannibal-corpse.extractor.url
  (:import java.net.URL)
  (:require [clojure.string :as string]
            [cannibal-corpse.util :refer :all]))


(defn parse-url
  "http://www.amazon.co.jp/foo/bar => {:domain amazon.co.jp
                                       :host www.amazon.co.jp
                                       :path /foo/bar}"
  [url]
  (let [result (java-object-to-hashmap (new URL url) host path)]
    (into
      result
      {:raw url
       :domain (string/replace
                 (:host result)
                 #"^www\."
                 "")})))


(defn- extract-amazon-product-id [path]
  path)


(defn extract-url [url]
  (let [url (parse-url url)]
    (cond
      (= "amazon" (:domain "amazon.co.jp"))
      {:site 'amazon
       :id (extract-amazon-product-id (:path url))})))
