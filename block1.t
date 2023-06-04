{
	int a; int b; float c; double d;
	a = 0; b = 0; c=0;d=0;
	{
		int b; b = 1;
		{
			int a; a = 2;
		}
		{
			int b; b = 3;
		}
		a = a + 1; b = b + 1;
	}
	while(true){
	    a = a + 1; b = b + 1;
	}

}
