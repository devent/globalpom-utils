package com.anrisoftware.globalpom.fileresourcemanager

/**
 * Resource to be saved.
 *
 * @see ResourceSaverTest
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class ResourceA implements Resource, Serializable {

	@Override
	public String getName() {
		"A"
	}

	@Override
	public void save(OutputStream stream) throws Exception {
		def out = new ObjectOutputStream(stream)
		out.writeObject(this)
		out.flush()
	}
}
