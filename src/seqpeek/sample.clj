(ns seqpeek.sample
  (:use [clojure.tools.cli :only [cli]]
        [incanter core io stats]
        [seqpeek.fastq :only [fastq-seq]]
        [seqpeek.file :only [make-line-seq]]))

(defn- parse-args
  "Argument parser for the sample-reads command."
  [args]
  (cli args
       ["-n" "--sample-size" "The number of reads to include" :flag false]))

(defn sample-records
  [filename n]
  (sample (fastq-seq-over filename) :size n :replacement false))

