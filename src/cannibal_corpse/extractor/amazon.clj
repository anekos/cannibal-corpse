(ns cannibal-corpse.extractor.amazon
  (:require [net.cgrand.enlive-html :as eh]))



(def selectors
  {:kindle [:#kindle_meta_binding_winner :td.price]})


(defn extract-price [html]
  (eh/select html [:#kindle_meta_binding_winner :td.price]))
