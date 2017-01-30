 <a name="#documentr_top"></a>

> **This project requires JVM version of at least 1.7**






<a name="documentr_heading_0"></a>

# gradle-plugin-java-create <sup><sup>[top](#documentr_top)</sup></sup>



> Easy generation of a gradle plugin in java






<a name="documentr_heading_1"></a>

# Table of Contents <sup><sup>[top](#documentr_top)</sup></sup>



 - [gradle-plugin-java-create](#documentr_heading_0)
 - [Table of Contents](#documentr_heading_1)
 - [Building the Package](#documentr_heading_2)
   - [*NIX/Mac OS X](#documentr_heading_3)
   - [Windows](#documentr_heading_4)
 - [Running the Tests](#documentr_heading_5)
   - [*NIX/Mac OS X](#documentr_heading_6)
   - [Windows](#documentr_heading_7)
   - [Dependencies - Gradle](#documentr_heading_8)
   - [Dependencies - Maven](#documentr_heading_9)
   - [Dependencies - Downloads](#documentr_heading_10)






<a name="documentr_heading_2"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_3"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_4"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_5"></a>

# Running the Tests <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_6"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`



<a name="documentr_heading_7"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests



<a name="documentr_heading_8"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloop', name: 'gradle-plugin-java-create', version: '0.0.1', ext: 'jar')

	compile(group: 'synapticloop', name: 'gradle-plugin-java-create', version: '0.0.1', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloop:gradle-plugin-java-create:0.0.1'

	compile 'synapticloop:gradle-plugin-java-create:0.0.1'
}
```





<a name="documentr_heading_9"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>gradle-plugin-java-create</artifactId>
	<version>0.0.1</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_10"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### compile dependencies

  - synapticloop:templar:1.3.0: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.3.0/view#files/synapticloop/templar/1.3.0) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.3.0|jar))
  - commons-cli:commons-cli:1.3.1: (It may be available on one of: [bintray](https://bintray.com/commons-cli/maven/commons-cli/1.3.1/view#files/commons-cli/commons-cli/1.3.1) [mvn central](http://search.maven.org/#artifactdetails|commons-cli|commons-cli|1.3.1|jar))
  - synapticloop:simplelogger:1.1.1: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/simplelogger/1.1.1/view#files/synapticloop/simplelogger/1.1.1) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|simplelogger|1.1.1|jar))


### runtime dependencies

  - synapticloop:templar:1.3.0: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.3.0/view#files/synapticloop/templar/1.3.0) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.3.0|jar))
  - commons-cli:commons-cli:1.3.1: (It may be available on one of: [bintray](https://bintray.com/commons-cli/maven/commons-cli/1.3.1/view#files/commons-cli/commons-cli/1.3.1) [mvn central](http://search.maven.org/#artifactdetails|commons-cli|commons-cli|1.3.1|jar))
  - synapticloop:simplelogger:1.1.1: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/simplelogger/1.1.1/view#files/synapticloop/simplelogger/1.1.1) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|simplelogger|1.1.1|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)

--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
