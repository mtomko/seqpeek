(ns seqpeek.fastq-test
  (:use clojure.test
        seqpeek.fastq)
  (:import seqpeek.fastq.Fastq))

(def fastq-record1
  ["@TE04D:00028:00073"
   "TAGGAAGACAGTACGTATTCTTGGTCTATATTATCTTGTGAGTACCGTGTCTTA"
   "+"
   ";>>BABCD@CCCB;?49919@9?@ABA>BB;<=BBB>B;;8<AA@>@@999@19"])

(def fastq-record2
  ["@TE04D:00031:00075"
   "TAAGGAAGACTAGCATCTTTGTCGTTTATTACATTGTGTGAAGAGACGACACGCCAGGCTTGC"
   "+"
   "@@6;8;BC?AB@;<999A<BA@B?<;;19C@<<=CCBCCC@BA>B@BBB;;;6.0,.....14/"])

(def fastq-record3
  ["@TE04D:00033:00077"
   "TAAGCGGACAGATCAGTTCTTGTCGTTACTATATCTGTTGACGAGAACGAGGTCCACCACTCTCACCCA"
   "+"
   ">@49544959BBBCCCCCBCC@C@BCD@DDBBBCCCACCB=@><@<@@>9958991/,,,,*,,,0000"])

(def fastq-sequence
  (seq (list fastq-record1 fastq-record2 fastq-record3)))

(deftest test-fastq-sequences
  (testing "seqpeek.fastq/fastq-sequences"
    (is (= (seq (list "TAGGAAGACAGTACGTATTCTTGGTCTATATTATCTTGTGAGTACCGTGTCTTA"))
           (fastq-sequences fastq-record1))))
  (testing "seqpeek.fastq/fastq-seq")
  (is (= (seq (list
               (Fastq.
                "@TE04D:00028:00073"
                "TAGGAAGACAGTACGTATTCTTGGTCTATATTATCTTGTGAGTACCGTGTCTTA"
                ";>>BABCD@CCCB;?49919@9?@ABA>BB;<=BBB>B;;8<AA@>@@999@19")))
           (fastq-seq fastq-record1))))
