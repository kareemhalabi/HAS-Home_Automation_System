package ca.mcgill.ecse321.HAS.application;

import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;
import ca.mcgill.ecse321.HAS.view.HASPage;


public class HASApp
{
	public static void main(String[] args)
	{
		// load model
		PersistenceHAS.loadHASModel();

		// start UI
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new HASPage().setVisible(true);
			}
		});
	}

}
