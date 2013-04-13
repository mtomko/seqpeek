(ns seqpeek.fastq
  (:use seqpeek.file))

;; Define a FASTQ record type with a toString method
(defrecord Fastq [id sequence qual]
  Object
  (toString [_]
    (str \@ id \newline sequence \newline \+ id \newline qual)))

(defn fastq-sequences
  "Returns the second of every 4 elements in the provided 
  collection. In a FASTQ file, this corresponds to the sequences."
  [coll]
  (take-nth 4 (drop 1 coll)))

(defn fastq-seq
  "Reads a sequence of strings and returns a lazy
  list of FASTQ records represented as maps."
  [coll]
  (for [[id sequence _ qual] (partition 4 coll)] 
    (Fastq. (subs id 1) sequence qual)))

(defn fastq-file-seq
  [filename]
  (-> filename file-reader line-seq fastq-seq))

;(defn fastq-to-string)

(defn phred-atoi
  "Returns the phred-atoi function appropriate for the given
  FASTQ dialect."
  [dialect]
  (letfn [(phred-atoi-fn [base qual]
            (- (int qual) base))]
    (case dialect
      :illumina (partial phred-atoi-fn 64)
      :iontorrent (partial phred-atoi-fn 33)
      :sanger (partial phred-atoi-fn 33)
      :solexa (partial phred-atoi-fn 64)
      (partial phred-atoi-fn 64))))

