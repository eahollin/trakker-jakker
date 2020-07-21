# trakker-jakker project

TrakkerJakker is a component of the GeoTrak (tm) platform, Copyright 2020 Ed Hollingsworth.
It is freely licensed under the Open Source MIT License.

## Configuring the application

Modify the "graphql.endpoint" property foundd in the src/main/resources/application.properties file to reflect the GraphQL endpoint of the Trakker service deployed in your environment.

To customize the configuration at runtime, simply add the environment variable GRAPHQL_ENDPOINT to your environment when launching the application, and it will override the default value.