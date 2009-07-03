This SVN repository contains versions of JML specification files for
various versions of the Java API, for some other libraries, and for
JML runtime files.

The repository is arranged in the usual way with trunk, branches and tags
directories.  Within the trunk directory are subdirectories for various
versions of Java, correspondingly named.  There are some API changes from
version to version, particularly from 1.4 to 1.5 with the introduction of
generics.  However, most APIs stay the same from version to version.  Java 1.6
mostly just adds to Java 1.5.  The java4 directory targets JDK 1.4.2.

Accordingly, at least for now, while these directories are being populated,
we will not duplicate specification files across versions.  Only if the spec
file is altered for a new version will we put that new version in the
corresponding directory.  The specification path can be suitably constructed 
to pull in all the relevant specification files.
For example, if running version
- 1.4, the path would be something like    trunk/java4
- 1.5, the path would be something like    trunk/java5:trunk/java4
- 1.6, the path would be something like    trunk/java6:trunk/java5:trunk/java4
The search order across the directory path will ensure that the correct file
is found.

If it proves useful at some later date to make full copies in each version
subdirectory, we can readily do that.  You can make a single consolidated
directory easily with something like these commands, where 'consolidatedspecs'
is a new (non SVN!) directory:
    mkdir consolidatedspecs
	cp -r C:/home/projects/JMLspecs/trunk/java4/* consolidatedspecs
	cp -r C:/home/projects/JMLspecs/trunk/java5/* consolidatedspecs
	cp -r C:/home/projects/JMLspecs/trunk/java6/* consolidatedspecs
    find consolidatedspecs -name ".svn" -exec rm -rf  '{}' \;

[ I considered making each version subdirectory a separate branch, but 
thought that that would end up simply in a confusion of branches and merges. 
It also would make it harder to work with the directories side by side. ]

Branches are to be used as is common in SVN: to hold specific variations 
for temporary or unusual purposes.  Please name them descriptively.

David Cok
1 June 2008

