[![Build Status](https://travis-ci.org/marshmallouws/CA3_backend.svg?branch=master)](https://travis-ci.org/marshmallouws/CA3Backend)

- Clone or fork project (delete .git folder or change remote when cloning)
- Activate project on Travis
- Create two local databases named CA3 and CA3_test or change the used databasenames in config.properties
- Create a remote database on your droplet

## Travis CI
- Find project on Travis.org and make sure CI has been enabled
- Create two Environment Variables with names and values as sketched below (must be done in two steps):
  * REMOTE_PW : Value for script_user
  * REMOTE_USER : script_user
- If build passes, push to github and see the Travis build and deploy project
