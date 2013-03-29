#!/bin/sh

# stolen from http://github.com/daveray/clawk
(echo '#!/usr/bin/env java -jar"; cat target/*-standalone.jar) > target/seqpeek
chmod u+x target/seqpeek
