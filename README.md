# seqpeek

A simple library for exploring next-gen sequencing data files.

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

## Examples

    $ seqpeeq count -n 54 test.fastq
    3828933

## License

Copyright © 2013 Mark Tomko

Distributed under the Eclipse Public License, the same as Clojure.
