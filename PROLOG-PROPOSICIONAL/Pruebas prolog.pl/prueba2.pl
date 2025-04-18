q :- a, p, s.
q :- c.

a.
p :- b, !, c.
p :- c.
b.
c :- fail.
s :- fail.
