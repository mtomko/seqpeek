(ns seqpeek.count-reads
  (:require seqpeek.fastq
            seqpeek.file
            seqpeek.seq)
  (:use [clojure.tools.cli :only[cli]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer. %)]
       ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer. %)]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn- fastq-seq-over
  [filename]
  (-> filename seqpeek.file/make-line-seq seqpeek.fastq/fastq-seq))

(defn- build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (let [[ub, lb] [(:min-length options) (:max-length options)]]
    (cond (and (nil? ub) (nil? lb)) (fn [_] true)
          (nil? lb) (fn [x] (< x ub))
          (nil? ub) (fn [x] (>= x lb))
          :else (fn [x] (and (< x ub) (>= x lb))))))

(defn- count-reads-for-file
  "Counts reads in the input file matching the provided filter."
  [filename seqfilter]
  (let [fseq (fastq-seq-over filename)
        sequences (map :seq fseq)]
    (count (filter seqfilter sequences))))

(defn count-reads
  "The entry point for the count-reads command."
  [args]
  (let [[options files banner] (parse-args args)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (let [seqfilter (build-filter options)
          print-filenames (> 1 (count files))]
      (doseq [filename files]
        (when print-filenames println (str filename ": "))
        (println  (str "\t" (count-reads-for-file filename seqfilter)))))))
