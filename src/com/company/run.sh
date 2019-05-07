#!/bin/sh
#SBATCH -p short # eile
#SBATCH -N1  # keliuose kompiuteriuose (trecioje programoje nenaudojame MPI, todel tik 1)
#SBATCH -c12 # kiek procesoriu viename kompiuteryje
#SBATCH -C alpha # telkinys (jei alpha neveikia, bandykite beta)

javac Main.java
javac Grid.java
javac Conway.java
java Main