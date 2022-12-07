## Perl

turns out awk is also terrible for handling capturing groups, so we're back to perl

part 1:

```
perl -ne '
BEGIN {
  my @path;
  my %dir_sizes;
};
if (/\$ cd \.\./) {
  pop @path;
}
elsif (/\$ cd (.*)/) { 
  push @path, $1;
}
elsif (/(\d+) (.*)/) {
  foreach $d (@path) {
    $key = $key . $d . "/";
    $dir_sizes{$key} += $1;
  };
  undef $key
};
END {
  for my $value (values %dir_sizes) {
    if ($value <= 100000) {
      $total += $value;
    }
  };
  print "\n$total\n"
}' input.txt
```

alternative END block for part 2:

```
END {
  $used_space = $dir_sizes{"//"};
  $free_space = 70000000 - $used_space;
  $space_required = 30000000 - $free_space;
  $answer = 70000000;
  for my $value (values %dir_sizes) {
    if ($value > $space_required) {
      $answer = ($answer, $value)[$answer > $value];
    }
  };
  print "\nTotal Space Used: $used_space\nSpace Required: $space_required\nSize of folder to delete: $answer\n"
}
```
