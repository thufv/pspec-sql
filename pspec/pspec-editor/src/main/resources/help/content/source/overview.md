# Overview
## PSpec
PSpec is a policy language for specifying data usage restrictions for big data analytic systems, and is developed by Tsinghua University and Intel Labs China.
With PSpec, policy writers can define fine-grained data usage policies for data analytic tasks. For example, a policy write may specify certain data resoures are *forbiden* for certain users, or can be accessed only after certain *desensitize operaitons*. During runtime, each submitted query is checked according to the semantics of PSpec to decide whether or not the query is allowed.

A PSpec policy consists of two parts, [vocabulary part](vocabulary.html) and [policy part](policy.html). Bacially, a vocabulary defines *user categories* and *data categories*, which denote the users and data elements to be regulated respectively.
A policy defines a set of *rules*, each of which specifies under what *restrictions* can certain *user categories* access certain *data categories*.

## PSpec Editor
The goal of PSpec Editor is to facilitate policy writers write, manage and analyze the PSpec policy. PSpec Editor is still under active development, and currently it supports the following main features:

* Graphically edit PSpec vocabularies/policies
* Load/save PSpec vocabularies/policies
* Analyze PSpec policies, including simplification, detecting redundent rules and checking inconsistent rules
* Visualize the relations between PSpec rules

## About This Document
In this document, we mainly discuss how to use the PSpec Editor to edit/analyze PSpec vocabularies/policies. For the detailed information about PSpec language, e.g., precise semantics, policy analysis algorithms, please refer to our technical report [TODO]().