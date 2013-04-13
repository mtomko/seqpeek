(ns seqpeek.sample
  (:require [incanter.stats :as stats])
  (:use [clojure.tools.cli :only [cli]]
        [seqpeek.command]
        [seqpeek.fastq :only [fastq-file-seq]]))

(defn- parse-args
  "Argument parser for the sample-reads command."
  [args]
  (cli args
       ["-n" "--sample-size" "The number of reads to include" :flag false :parse-fn #(Integer/parseInt %)]))

(defn sample-records
  [filename n]
  (stats/sample (fastq-file-seq filename) :size n :replacement false))

(defn- sample-body
  "The body of the sample command"
  [options files]
  (if-let [n (:sample-size options)]
    (doseq [r (sample-records (first files) n)]
      (println (str r)))))

(defn sample
  "The entry point for the sample command."
  [args]
  (command parse-args args sample-body))
