# Mnemosyne 2 Csv #

	git clone git@github.com:oschrenk/mnemosyne2csv.git
	cd mnemosyne2csv
	make sbt
	./sbt "run /path/to/mnemosyne.xml"

Creates a csv file for each category in the same directory as the Mnemosyne file, and, ignoring all inverse entries, writes out each question and answer tuple with each word separated by a tab and each entry by a newline.