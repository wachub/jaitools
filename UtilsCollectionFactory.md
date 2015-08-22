# CollectionFactory #

**package:** jaitools.utils

## Description ##

This class contains a number of static convenience methods to create generic Lists and Maps. It works on the DRY (Don't Repeat Yourself) principle.

## Examples of use ##

DRY means not having to repeat the types on both sides of the assignment statement when creating a new Collection.
```
// Rather than doing this...
List<MySortOfClass> verbose = new ArrayList<MySortOfClass>();

// ...you can do this...
List<MySortOfClass> elegant = CollectionFactory.newList();
```

It's even nicer when the typing gets more complex...
```
List<Map<Foo, Bar>> compound = CollectionFactory.newList();
```

Other methods...
```
Set<Foo> set = CollectionFactory.newSet();

TreeSet<Foo> treeSet = CollectionFactory.newTreeSet();

Map<Foo, Bar> map = CollectionFactory.newMap();

TreeMap<Foo, Bar> treeMap = CollectionFactory.newTreeMap();

Stack<Foo> stack = CollectionFactory.newStack();
```