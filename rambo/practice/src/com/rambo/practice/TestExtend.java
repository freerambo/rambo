package com.rambo.practice;

class Base

{

	int i = 99;

	public void amethod()

	{

		System.out.println("Base.amethod()");

	}

	Base()

	{

		amethod();

	}

}

public class TestExtend extends Base

{

	int i = -1;

	public static void main(String args[])

	{

		Base b = new TestExtend();

		System.out.println(b.i);

		b.amethod();

	}

	public void amethod()

	{

		System.out.println("Derived.amethod()");

	}

}
