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


(defn- extract-amazon-product-id [url]
  (second
    (re-find
      #".*?/?(?:[a-z]p|ASIN)/(?:product/)?([^/]+).*"
      (:path url))))


(defn extract-url
  "http://www.amazon.co.jp/dp/XYZ => {:site amazon.co.jp
                                      :id XYZ}"
  [url]
  (let [url (parse-url url)]
    (cond
      (= "amazon.co.jp" (:domain url))
      {:site 'amazon.co.jp
       :id (extract-amazon-product-id url)})))
