#!/bin/bash
#
# script to run the artikel web application.
#
# replace the workingDir variable according to your setup.
# replace the <version> tag below with the version you have.
#
#
# Note: Make sure you have the Open Policy Agent server running, before you start the application.
#

baseFolder=/opt/artikel
applicationFolder=${baseFolder}/artikel-<version>

java -jar ${applicationFolder}/artikel.jar ${baseFolder}/config.yaml

