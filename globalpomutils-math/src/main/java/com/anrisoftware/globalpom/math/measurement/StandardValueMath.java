/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.math.measurement;

/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.sqrt;

/**
 * Calculation of error propagation using the standard deviation.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class StandardValueMath {

    public static double subUncertainty(double sa, double sb) {
        return sqrt(pow2(sa) + pow2(sb));
    }

    public static double mulUncertaintly(double a, double sa, double b,
            double sb, double z) {
        return sqrt(pow2(sa / a) + pow2(sb / b)) * abs(z);
    }

    public static double mulUncertaintly(double b, double sb, double z) {
        return sqrt(pow2(sb / b)) * abs(z);
    }

    public static double logUncertainty(double a, double sa) {
        return sa / abs(a);
    }

    public static double expUncertainty(double sa, double z) {
        return sa * abs(z);
    }

    public static double reciprocalUncertaintly(double a, double sa, double z) {
        return abs(z) * sa / a;
    }

    private static double pow2(double value) {
        return value * value;
    }

}
