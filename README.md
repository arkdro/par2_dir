# Create par2 in a different location

The whole purpose is to create par2 files in a location that is different
from the original data file.

# Call par2 on links

- create a link in a predefined way/location
- call par2 on that link to build par2 files
- remove the link

# Parameters

- input dir
- output dir
- input dir part to delete. The rest is created in the output dir.
- the rest of arguments constitues a command to execute.

# Example

`par2_dir -i /usr/export/vol01/users/dept01 -o /mnt/home -d /usr/export/vol01/users -- par2 c -r5`

It creates the output path that starts with the following: `/mnt/home/dept01`
And it calls the command that is passed as the rest of arguments (in this case
it's `par2 c -r5`) using the current link as the last parameter for this command.
The whole command looks like this:
`par2 c -r5 /mnt/home/dept01/some_file.dat`

## Usage

FIXME

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
