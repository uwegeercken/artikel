#!/bin/bash
#
# script to run the artikel web application.
#
# replace the workingDir variable according to your setup.
# replace the <version> tag below with the version you have.
#

workingDir=/opt/artikel
applicationDir=${workingDir}/artikel<version>

java -jar ${applicationDir}/artikel.jar ${workingDir}/config.yaml

