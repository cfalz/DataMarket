#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
psql -h 127.0.0.1 biomarketdb < $DIR/../src/create_tables.sql
#psql -h 127.0.0.1 BioMarketDB < $DIR/../src/create_indexes.sql
psql -h 127.0.0.1 biomarketdb < $DIR/../src/load_data.sql
