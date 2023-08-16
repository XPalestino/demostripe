# Gymki API

## Requirements

- `Java` 17
- `MySQL` 8

## Export environment variables:

### Required Parameters

| Name | Type | Description |
|:-----|------|:------------|
|      |      |             |

### Optional Parameters

| Name                       | Type    | Description                                                                                                                                                       |
|:---------------------------|:--------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `DB_HOST`                  | String  | Database host used by application.<br/>**Default value is**: `localhost`                                                                                          |
| `DB_PORT`                  | Integer | Database port used by application.<br/>**Default value is**: `3306`                                                                                               |
| `DB_SCHEMA`                | String  | Database schema used by application.<br/>**Default value is**: `gymki`                                                                                            |
| `DB_USERNAME`              | String  | Database username used by application.<br/>**Default value is**: `iwa`                                                                                            |
| `DB_PASSWORD`              | String  | Database password used by application.<br/>**Default value is**: `demo`                                                                                           |
| `MIGRATION_MYSQL_USER`     | String  | Database username used to run the migrations.<br/>**Default value is**: `iwa `                                                                                    |
| `MIGRATION_MYSQL_PASSWORD` | String  | Database password used to run the migrations.<br/>**Default value is**: `demo`                                                                                    |
| `SQL_QUERY_STRINGS`        | String  | Extra parameters used in database connection string.<br/>**Default value is**: `useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true` |
| `SERVER_PORT`              | Integer | Application server port where the app is running.<br/>**Default value is**: `8081`                                                                                |

## Useful commands

Execute the following commands in the root directory

## Building

To build the project

```shell script
make build
```

## Running project

To run project using `local` spring profile

```shell script
make run
```

To run project using a different spring profile

```shell script
SPRING_PROFILES_ACTIVE=<profile-name> make run
```

To run project with docker, first create .env file at `/docker` directory, then copy values from .env-template

Run database container

```shell
make docker-db
```

To run the API service, as a first step, it is necessary to log in to Docker to download the image distroless, you can execute the following line

```shell
export YOUR_TOKEN=<token>
echo $YOUR_TOKEN | docker login ghcr.io -u <username> --password-stdin
```

Remember to replace `token` with your token generated from your personal account and `username` with your GH username.

Download the docker distroless image by executing the following line, the version number depends on the [version](https://github.com/iwa-consolti/iwa-distroless-java-base-image/pkgs/container/iwa-distroless-java-17-base) required in dockerfile

```shell
docker pull ghcr.io/iwa-consolti/iwa-distroless-java-17-base:<version>
```

Run service container

```shell
make build-api
```

## Linting

To check format and checkstyle

```shell script
make lint
```

## Apply format

To format code

```shell script
make fmt
```

# Git Hooks

**Pre-commit**

Install pre-commit (https://pre-commit.com/#install)

Using pip

```
❯ pip install pre-commit
```

Using brew(mac)

```
❯ brew install pre-commit
```

Check version

```
❯ pre-commit --version
pre-commit 3.3.1
```

[Install the git hook scripts](https://pre-commit.com/#3-install-the-git-hook-scripts)

```
❯ pre-commit install
pre-commit installed at .git/hooks/pre-commit
```

**Usage**
pre-commit hooks will be triggered before pushing changes automatically but if you want to run it locally you can use

```
❯ pre-commit run --all-files
Validate GitHub Workflows................................................Passed
Validate Dependabot Config (v2)..........................................Passed
codespell................................................................Passed
```

## Run Contrast SCA locally

Install CLI tool by running the following command:

```shell script
npm install -g @contrast/contrast@2
```

Check the installed version of CLI tool by running:

```
contrast version
```

Configure your Contrast credentials.
You can grab these values from Contrast portal (User Settings > Profile) and copy the UUID from the URL this value represents the organization ID.

```
➜  gymki git:(develop) contrast auth \
    --api-key <your-api-key> \
    --authorization <your-auth-key> \
    --host https://ce.contrastsecurity.com \
    --organization-id <your-organization-id>
```

Run the audit command in the root directory of your project:

```shell script
contrast audit
```

