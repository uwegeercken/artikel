#!/bin/bash
#
# script to run the artikel web application.
#
# the config.yaml file needs to be in the same location as this script.
#
# Note: Make sure you have the Open Policy Agent server running, before you start the application.
#

applicationVersion=artikel-${project.version}

scriptPath=$(dirname -- "$(readlink -f "${BASH_SOURCE}")")
applicationDir=${scriptPath}/${applicationVersion}

java -jar ${applicationDir}/artikel.jar ${scriptPath}/config.yaml
