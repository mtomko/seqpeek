(ns seqpeek.filter-reads
  (:require [clojure.java.io :as io]
            [incanter.stats :as stats]
            [seqpeek.bio :as bio])
  (:use [clojure.core.match :only [match]]
        [clojure.tools.cli :only [cli]]
        [seqpeek.command]))

(defn- parse-args
  "Argument parser for the filter-reads command"
  [args]
  (cli
   args
   ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer/parseInt %)]
   ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer/parseInt %)]))

(defn build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (match [[(:min-length options) (:max-length options)]]
           [[nil nil]] (constantly true)
           [[lb nil]]  (fn [s] (>= (count s) lb))
           [[nil ub]]  (fn [s] (<= (count s) ub))
           [[lb ub]]   (fn [s] (and (>= (count s) lb)
                                    (<= (count s) ub)))))

(defn- filter-reads-in-coll
  [pred coll]
  (let [matching (filter pred coll)]
    (doseq [rec matching]
      (println (str rec)))))

(defn- filter-reads-in-file
  [pred file]
  (let [fileseq (bio/fastq-file-seq file)]
    (filter-reads-in-coll pred fileseq)))

(defn- filter-reads-command
  "The body of the filter-reads command"
  [options files]
  (when (< 0 (count files))
    (let [file (first files)
          seqfilter (build-filter options)]
      (filter-reads-in-file seqfilter file))))

(defn filter-reads
  [args]
  (command parse-args args filter-reads-command))
