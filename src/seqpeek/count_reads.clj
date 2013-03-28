(ns seqpeek.count-reads
  (:require [seqpeek.seq])
  (:use [clojure.tools.cli :only[cli]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer. %)]
       ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer. %)]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn count-reads
  "The entry point for the count-reads command."
  [dialect args]
  (let [[options files banner] (parse-args args)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (println options)))

(defn- build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (let [[ub, lb] [(:min-length options) (:max-length options)]]
    (cond (and (nil? ub) (nil? lb)) (fn [_] true)
          (nil? lb) (fn [x] (< x ub))
          (nil? ub) (fn [x] (>= x lb))
          :else (fn [x] (and (< x ub) (>= x lb))))))
