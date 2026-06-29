# Hands On 6 — Criteria Query

## The problem Criteria Query solves

Picture an e-commerce search page (the handout uses Amazon's laptop search
as the example). The left-hand filter panel offers several independent
criteria:

- Customer review
- Hard disk size
- RAM size
- CPU speed
- Operating system
- Weight
- CPU

A user might select **any combination** of these — one filter, three
filters, all seven, or none. The `WHERE` clause needed to satisfy that
search is different every single time, depending on which checkboxes were
ticked.

With a fixed HQL string (like the `@Query` methods used in Hands On 2–4),
you would need a different query for every possible combination of
filters — which is combinatorially unworkable. You can't hardcode the
`WHERE` clause when the number of clauses is only known at request time.

## Why Criteria Query fits this scenario

**Criteria Query lets you build the `WHERE` clause programmatically**,
adding one filter predicate per selected criterion at runtime, rather than
writing the predicate as a fixed string up front. This is exactly the
flexibility a dynamic search/filter form needs.

## Core building blocks

| Component | Role |
|---|---|
| `CriteriaBuilder` | Factory for constructing query elements — predicates (`equal`, `between`, `like`, `and`, `or`), orderings, etc. Obtained from the `EntityManager`. |
| `CriteriaQuery<T>` | Represents the overall typed query being built (the `SELECT ... FROM ... WHERE ...` skeleton). |
| `Root<T>` | Represents the entity being queried (the "FROM" part) — used to reference its fields when building predicates. |
| `TypedQuery<T>` | The final, executable query produced from the `CriteriaQuery`, analogous to what `@Query` produces but built in code instead of a string. |

## Worked example (laptop search scenario)

```java
public List<Product> searchLaptops(Map<String, Object> selectedFilters) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Product> cq = cb.createQuery(Product.class);
    Root<Product> root = cq.from(Product.class);

    List<Predicate> predicates = new ArrayList<>();

    // Always filter by the search keyword
    predicates.add(cb.like(root.get("name"), "%laptop%"));

    // Add a predicate ONLY if the user selected that filter
    if (selectedFilters.containsKey("ramSize")) {
        predicates.add(cb.equal(root.get("ramSize"), selectedFilters.get("ramSize")));
    }
    if (selectedFilters.containsKey("cpuSpeed")) {
        predicates.add(cb.greaterThanOrEqualTo(
            root.get("cpuSpeed"), (Comparable) selectedFilters.get("cpuSpeed")));
    }
    if (selectedFilters.containsKey("operatingSystem")) {
        predicates.add(cb.equal(root.get("operatingSystem"), selectedFilters.get("operatingSystem")));
    }
    // ... same pattern for hard disk size, weight, CPU, customer review ...

    cq.where(predicates.toArray(new Predicate[0]));

    TypedQuery<Product> query = entityManager.createQuery(cq);
    return query.getResultList();
}
```

The number of `predicates` added varies entirely based on which filters the
user actually selected — there's no fixed HQL string to maintain, and no
need to dynamically concatenate raw query strings (which would also risk
HQL/SQL injection if done carelessly).

## When to reach for Criteria Query vs. `@Query`

| Use `@Query` (HQL/JPQL) when... | Use Criteria Query when... |
|---|---|
| The query shape is fixed and known at compile time | The number/combination of filter conditions varies per request |
| A handful of optional parameters cover the variation (e.g. `@Param`) | The filter set is open-ended or comes from dynamic UI input |
| Readability of a plain query string is a priority | Type-safety and compile-time checking of field references matters more |

Reference: https://docs.oracle.com/javaee/6/tutorial/doc/gjrij.html
Further examples: https://howtodoinjava.com/hibernate/hibernate-criteria-queries-tutorial/
