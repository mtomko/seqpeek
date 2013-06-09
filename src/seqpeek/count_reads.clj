(ns seqpeek.count-reads
  (:require [clojure.java.io :as io]
            [seqpeek.bio :as bio])
  (:use [clojure.tools.cli :only [cli]]
        [seqpeek.command])
  (:import [java.io BufferedReader]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer/parseInt %)]
       ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer/parseInt %)]
       ["-i" "--stdin" "Read from standard input" :flag true]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (let [[lb ub] [(:min-length options) (:max-length options)]]
    (cond (and (nil? ub) (nil? lb)) (fn [_] true)
          (nil? lb) (fn [x] (<= (count x) ub))
          (nil? ub) (fn [x] (>= (count x) lb))
          :else (fn [x] (and (<= (count x) ub) (>= (count x) lb))))))

(defn count-matching
  "Counts reads matching the provided filter"
  [pred coll]
  (count (filter pred coll)))

(defn- count-matching-reads
  "Counts reads in the input file matching the provided filter."
  [seqfilter coll]
  (let [sequences (map :sequence coll)]
    (count-matching seqfilter sequences)))

(defn- count-matching-reads-in-files
  [seqfilter files]
  (let [print-filenames (< 1 (count files))]
    (doseq [filename files]
      (when print-filenames
        (println (str filename ": "))
        (print "\t"))
      (println (count-matching-reads seqfilter (bio/fastq-file-seq filename))))))

(defn- count-matching-reads-in-stdin
  [seqfilter]
  (println (count-matching-reads
            seqfilter
            (bio/fastq-seq (line-seq (io/reader *in*))))))

(defn- count-reads-command
  "The body of the count-reads command."
  [options files]
  (let [seqfilter (build-filter options)
        print-filenames (< 1 (count files))]
    (if (:stdin options) (count-matching-reads-in-stdin seqfilter)
      (count-matching-reads-in-files seqfilter files))))

(defn count-reads
  "The entry point for the count-reads command."
  [args]
  (command parse-args args count-reads-command))
