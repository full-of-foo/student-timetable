#!/bin/bash

pg_ctl -D /usr/local/var/postgres start
createuser design_patterns
createdb design_patterns_test -O design_patterns
