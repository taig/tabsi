---
layout: home
title:  "Home"
section: "home"
---

[![CircleCI](https://circleci.com/gh/Taig/tabsi/tree/master.svg?style=shield)](https://circleci.com/gh/Taig/tabsi/tree/master)
[![codecov](https://codecov.io/gh/Taig/tabsi/branch/master/graph/badge.svg)](https://codecov.io/gh/Taig/tabsi)
[![Maven Central](https://img.shields.io/maven-central/v/io.taig/tabsi_2.12.svg)](https://index.scala-lang.org/taig/tabsi)

# Tabsi

A simplistic approach to formatting tabular data. _Tabsi_ provides a data structure to create a `Table` consisting of `Column`s, `Row`s and `Cell`s. It contains functionality to pretty-print these table structures primarily intended as command-line output.

## What this is not ...

A fully-fledged table abstraction with an exhaustive feature set one might imagine. This library focuses on printing data as aligned columns. There are, for instance, no table headers or multiline cells.

## ... but what it should be

A safer version of itself that ensures correct dimensions on its operations at compile time. The developer is currently forced to carefully combine structures with matching dimensions as the library does currently not perform any checks on that. Mismatching dimensions can therefore lead to shrinking `Table`s based on the underlying behavior of `List.zip`.

# Installation

_Tabsi_ is available for Scala `2.12` and `2.11` (JVM and ScalaJS) via Maven Central.

```tut:invisible
import io.taig.tabsi.Build._
```

```tut:evaluated
println {
    s"""
     |libraryDependencies += "$organization" %% "$normalizedName" % "$version"
     """.stripMargin.trim
}
```

# Feature overview

Below are numerous examples that showcase the features of _Tabsi_ and how to use them. They all rely on the imports and sample data defined immediately hereafter.

```tut:silent
import io.taig.tabsi._
import io.taig.tabsi.implicits._

val cities = Column(
    Cell("Berlin"),
    Cell("Hamburg"),
    Cell("München"),
    Cell("Köln"),
    Cell("Frankfurt am Main"))

val population = Column(
    Cell("3.520.031"),
    Cell("1.787.408"),
    Cell("1.450.381"),
    Cell("1.060.582"),
    Cell("732.688"))

val table = Table.columns(cities, population)
```

### Format, print and printf

A `Table` has 3 operations:

1\. `format` uses the `Formatter` typeclass to convert a `Table[Cell]` to a `Table[String]`

```tut:book
// For every Column of the Table a Style has to be supplied
val styles = Styles(Style.Default, Style.Default)

table.format(styles)
```

2\. `print` uses the `Printer` typeclass to convert a `Table[String]` to a `String`

```tut:book
table.format(styles).print
```

3\. `printf` is a combination of `format` and `print`

```tut:book
table.printf(styles)
```

### Specify alignment for each column

The first column (cities) is left aligned, the right column (population) is right aligned which makes it easier to compare the numbers.

```tut:silent
val styles = Styles(
  Style.Default,
  Style.Default.copy(alignment = Alignment.Right)
)

table.printf(styles)
```

```tut:evaluated
println(table.printf(styles))
```

### Customize the output

Visual changes to the output can be applied by providing custom `Printer` instances.

```tut:silent
implicit val p: Printer[Row] = _.cells.mkString(" | ")
table.printf(styles)
```
 
```tut:evaluated
println(table.printf(styles))
```

```tut:invisible
implicit val p: Printer[Row] = Printer.row
```

### Width configuration

In the previous examples `Width.Auto` was used which sets the column width to its longest cell. To prevent columns from being too wide it is possible to define a `Width.Fixed` size.

```tut:silent
val styles = Styles(
  Style.Default.copy(width = Width.Fixed(10, ellipsize = None)),
  Style.Default.copy(alignment = Alignment.Right)
)

table.printf(styles)
```
 
```tut:evaluated
println(table.printf(styles))
```

Alternatively there is `Width.Limit` which either behaves like `Width.Auto` if the cells are shorter than the limit or like `Width.Fixed` if the cells exceed the size.

```tut:silent
val widthFixed = Styles(
  Style.Default.copy(width = Width.Fixed(30, ellipsize = None)),
  Style.Default.copy(alignment = Alignment.Right))

val widthLimitHigh = Styles(
  Style.Default.copy(width = Width.Limit(30, ellipsize = None)),
  Style.Default.copy(alignment = Alignment.Right))

val widthLimitLow = Styles(
  Style.Default.copy(width = Width.Limit(10, ellipsize = None)),
  Style.Default.copy(alignment = Alignment.Right))
```
 
#### Fixed width with high limit

```tut:evaluated
println(table.printf(widthFixed))
```

#### Limit width with high limit

```tut:evaluated
println(table.printf(widthLimitHigh))
```

#### Limit width with low limit

```tut:evaluated
println(table.printf(widthLimitLow))
```

### Ellipsizing long columns

When a cell is cut off due to a width limit, it is possible to better visualize this with an ellipsis. Without a given `Ellipsize` the cell is otherwise just cut off.

```tut:silent
val styles = Styles(
  Style.Default.copy(width = Width.Fixed(10, ellipsize = Some(Ellipsize.End))),
  Style.Default.copy(alignment = Alignment.Right)
)

table.printf(styles)
```
 
```tut:evaluated
println(table.printf(styles))
```

In this specific case, to avoid ambigiouties, a middle ellipsis might be preferable.

```tut:silent
val styles = Styles(
  Style.Default.copy(width = Width.Fixed(10, ellipsize = Some(Ellipsize.Middle))),
  Style.Default.copy(alignment = Alignment.Right)
)

table.printf(styles)
```
 
```tut:evaluated
println(table.printf(styles))
```

### Combining Tables and Columns

`Table` and `Column` have `append` and `prepend` operations that allow for combination.

```tut:silent
val numbers = Column(
  Cell("1."),
  Cell("2."),
  Cell("3."),
  Cell("4."),
  Cell("5."))

val styles = Styles(
  Style.Default,
  Style.Default,
  Style.Default.copy(alignment = Alignment.Right))

table prependLeft Table.columns(numbers) printf(styles)
```

```tut:evaluated
println(table prependLeft Table.columns(numbers) printf(styles))
```

### Adding empty cells

```tut:silent
val styles = Styles(
  Style.Default,
  Style.Default.copy(alignment = Alignment.Right)
)

val sum = Row(Cell.Empty, Cell("8.551.090"))

table appendBottom Table.rows(sum) printf(styles)
```

```tut:evaluated
println(table appendBottom Table.rows(sum) printf(styles))
```

### Structuring data as Rows

Creating a `Table` with `Column`s can feel unnatural and instead `Row`s might be a better fit.

```tut:silent
val berlin = Row(Cell("Berlin"), Cell("3.520.031"))
val hamburg = Row(Cell("Hamburg"), Cell("1.787.408"))
val munich = Row(Cell("München"), Cell("1.450.381"))
val cologne = Row(Cell("Köln"), Cell("1.060.582"))
val ffm = Row(Cell("Frankfurt am Main"), Cell("732.688"))

val table = Table.rows(berlin, hamburg, munich, cologne, ffm)

// Styles are always per Column
val styles = Styles(
  Style.Default,
  Style.Default.copy(alignment = Alignment.Right)
)

table.printf(styles)
```

```tut:evaluated
println(table.printf(styles))
```