(ns seqpeek.seq
  (:require [clojure.tools.cli :only[cli]])
  (:import java.io.File
           (org.biojava3.sequencing.io.fastq FastqReader
                                             IlluminaFastqReader
                                             SangerFastqReader
                                             SolexaFastqReader)))

(defn fastq-reader
  "Constructs a FASTQ reader appropriate for the provided machine type.
   Defaults to Illumina."
  [type]
  (case (clojure.string/lower-case type)
    "illumina" (IlluminaFastqReader.)
    "iontorrent" (SangerFastqReader.)
    "sanger" (SangerFastqReader.)
    "solexa" (SolexaFastqReader.)
    (IlluminaFastqReader.)))

(defn fastq-seq
  "Returns a sequence view of the FASTQ reader."
  [reader file]
  (seq (.read reader file)))

(defn sequences
  "Returns a lazy sequence of just the raw sequence data in the FASTQ"
  [reader file]
  (map #(.getSequence %) (fastq-seq reader file)))

(defn count-long-reads 
  [reader file n]
  (count (filter
          #(<= n (count %))
         (sequences reader file))))

