package fr.cactuscata.signgenerator;

public class Pair<T1, T2> {
	public T1 left;
	public T2 right;

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pair)) {
			return false;
		}
		Pair<?, ?> o = (Pair<?, ?>) obj;
		boolean cont = false;
		if (this.left == null) {
			cont = false;
			if (o.left == null) {
				cont = true;
			}
		} else {
			cont = this.left.equals(o.left);
		}
		if (!cont) {
			return false;
		}
		if (this.right == null) {
			cont = false;
			if (o.right == null) {
				cont = true;
			}
		} else {
			cont = this.right.equals(o.right);
		}
		return cont;
	}

	public Pair(T1 left, T2 right) {
		this.left = left;
		this.right = right;
	}
}
