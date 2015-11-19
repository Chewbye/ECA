#!/bin/bash

mkdir lemmatisation;

for fichier in $1/*; do
	if [ -d $fichier ]; then
		nomDossier=$(basename $fichier);
		mkdir lemmatisation/$nomDossier;
		for fichier2 in $fichier/*; do
			nomFichier=$(basename $fichier2);
			contenuFichier=`cat $fichier2`;
			contenuFichier=`echo "${contenuFichier//\“/}"`;
			contenuFichier=`echo "${contenuFichier//\”/}"`;
			contenuFichier=`echo "${contenuFichier//\’/\'}"`;
			contenuFichier=`echo "${contenuFichier//\‘/\'}"`;
			contenuFichier=`echo "${contenuFichier//\"/}"`;
			echo $contenuFichier | tree-tagger-english >> lemmatisation/$nomDossier/$nomFichier;
		done
	fi
done
