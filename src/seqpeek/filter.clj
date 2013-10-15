(ns seqpeek.filter
  (:require [clojure.java.io :as io]
            [incanter.stats :as stats]
            [seqpeek.bio :as bio])
  (:use [clojure.core.match :only [match]]
        [clojure.tools.cli :only [cli]]
        [seqpeek.command])
  (:import [java.io BufferedReader]))

(def filter-args
  [["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer/parseInt %)]
   ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer/parseInt %)]])

(defn build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (match [[(:min-length options) (:max-length options)]]
           [[nil nil]] (constantly true)
           [[lb nil]]  (fn [s] (>= (count s) lb))
           [[nil ub]]  (fn [s] (<= (count s) ub))
           [[lb ub]]   (fn [s] (and (>= (count s) lb)
                                    (<= (count s) ub)))))
