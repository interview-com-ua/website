Project Description
===================
Interview-It is a stunning tool that helps applicant improve results of visiting interview.
All the experiences (questions) is stored in interview. it service and accessible to repeat in useful form at any time.
Also, possible to prepare to the interview, repeating questions as a flash cards.This can be used as warm up for interviews.
Further gained experiences would be added according to companies.This will be very useful.

Maven settings
==============
Add next code to maven settings (usual ~/.m2/settings.xml)

    <profiles>
        <profile>
            <id>compiler</id>
            <properties>
                <JAVA_7_HOME>Path to java7 home folder</JAVA_7_HOME>
            </properties>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>compiler</activeProfile>
    </activeProfiles>
