# Social Snapshot Plugin
===============

Open source follow up of the Diploma thesis Storage and Visualization of Heterogeneous Data from Online Social Networks

This project holds the basic modules and first exemplary implementations of further extensions to the Gephi Graph Framework to facilitate visualization and analysis of Social network snapshots obtained from a Facebook Crawler

## Development Machine setup

At first begin with downloading and installing the various development applications and database.
	
 - sudo apt-get install mongodb
 - sudo apt-get install netbeans
 - sudo apt-get install git

	Clone the git repository to obtain the project files for Gephi as well as the Snapshot plugin repository.
	
 - git clone https://github.com/gephi/gephi.git (Read-Only)
 - git clone https://github.com/christianbors/snapshot-plugin
	
	Browsed in the Gephi repository apply a patch located in the Snapshot plugin repository:

 * git am ../ssanalytics/gephi-maven-patches/0001-added-SnapshotPlugin-dependencies.patch

	This adds the Snapshot plugin's dependency to the gephi application. As a result, if it has been built properly, the plugin is automatically included in the application without having to manually add it, this is convenient for development purposes. One last thing that needs to be done to get the plugin working in its entirety is to copy the config file from the gephi-maven-patches folder in the Snapshot plugin repository into:
	
	<gephi-repository>/modules/application/config.json

	Please use netbeans as primary development and build tool, first import the Snapshot plugin into the project list. Afterwards import the entire Gephi project, in this project open the gephi-app project located in the module folder of gephi into the project list as well. Initially the Snapshot plugin as well as gephi need to be built, this will trigger downloading all dependencies since both projects are based on the Maven building and dependency tool. Start the application by running gephi-app. Gephi modules can be browsed by including them in the workspace.
	
	* See [Gephi](http://gephi.org/toolkit)
	* See [Netbeans](https://netbeans.org/)
	* See [Netbeans](https://netbeans.org/)
	* See [Source Code](https://github.com/christianbors/snapshot-plugin)
	* See [MongoDB](http://www.mongodb.org/)