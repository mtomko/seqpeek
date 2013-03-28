(ns seqpeek.fastq-test
  (:use clojure.test
        seqpeek.fastq))

(def fastq-record1
  ["@JE07D:00028:00073"
   "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGATACGCTGCTTTA"
   "+"
   ";>>BABCD@CCCB;?49919@9?@ABA>BB;<=BBB>B;;8<AA@>@@999@19"])

(deftest test-fastq-sequences
  (testing "seqpeek.fastq/fastq-sequences"
    (is (= (seq (list "TAAGGAGAACGATCGATTTCTTGGCTTTATATATCTTGTGGATACGCTGCTTTA"))
           (fastq-sequences fastq-record1)))))
           
