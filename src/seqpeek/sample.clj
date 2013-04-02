(ns seqpeek.sample
  (:use [clojure.tools.cli :only [cli]]
        [seqpeek.fastq :only [fastq-seq]]
        [seqpeek.file :only [make-line-seq]]))

(defn- parse-args
  "Argument parser for the sample-reads command."
  [args]
  (cli args))
