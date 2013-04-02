# seqpeek

A simple tool for exploring next-gen sequencing data files. Currently supports only FASTQ files, 
but support is planned for other file formats.

## Installation

Download from http://github.com/mtomko/seqpeek

Run:
    $ lein uberjar
    $ make-sh.sh

## Usage

    $ java -jar seqpeek-0.1.0-standalone.jar <command> [args] [files]

## Options

###count
Counts reads based on simple criteria. Currently supports length-based
filters only.

###plot
Plots read statistics. Currently supports only length-based plots. This
can be useful for sequencing processes that generate reads of variable
length.

## Examples

    $ seqpeek count -n 54 test.fastq
    3828933
    
    # plot a histogram of read lengths
    $ seqpeek plot -l test.fastq

## License

Copyright © 2013 Mark Tomko

Distributed under the Eclipse Public License, the same as Clojure.
